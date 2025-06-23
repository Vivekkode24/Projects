#Auto load .env
from dotenv import load_dotenv
load_dotenv()

#imports
import os, time, requests, praw, pandas as pd
import nltk
from datetime import datetime
from bs4 import BeautifulSoup
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
from nltk.stem import PorterStemmer
from nltk.tokenize import word_tokenize
import streamlit as st
import matplotlib.pyplot as plt

def setup_nltk():
    nltk.download('punkt')
    return True
_ = st.cache_resource(setup_nltk)()

#Load credentials from environment
REDDIT_CLIENT_ID     = os.getenv('REDDIT_CLIENT_ID')
REDDIT_CLIENT_SECRET = os.getenv('REDDIT_CLIENT_SECRET')
REDDIT_USER_AGENT    = os.getenv('REDDIT_USER_AGENT', 'ethical-sentiment-app')
SLACK_WEBHOOK_URL    = os.getenv('SLACK_WEBHOOK_URL')

#Caching and Governance notes
#We cache API calls to avoid rate limits (TTL=1h).
#Only public Reddit data is fetched; no PII is stored.

@st.cache_data(ttl=3600)
def fetch_reddit_posts(brand, limit=50):
    reddit = praw.Reddit(
        client_id=REDDIT_CLIENT_ID,
        client_secret=REDDIT_CLIENT_SECRET,
        user_agent=REDDIT_USER_AGENT
    )
    posts = []
    for post in reddit.subreddit('all').search(brand, limit=limit):
        posts.append(post.title + ' ' + post.selftext)
    return posts

@st.cache_data(ttl=3600)
def fetch_news_headlines(brand, limit=50):
    url = f"https://news.google.com/search?q={brand.replace(' ', '%20')}&hl=en-US&gl=US&ceid=US:en"
    resp = requests.get(url)
    soup = BeautifulSoup(resp.text, 'html.parser')
    return [h3.text for h3 in soup.select('article h3')[:limit]]

ps = PorterStemmer()
def classify_topic(text):
    stems = [ps.stem(tok) for tok in word_tokenize(text.lower())]
    keyword_sets = {
        'Environmental': ['carbon','climat','sustain','emiss','green','ecolog','pollut','renew','solar','environ','wast','biodivers'],
        'Labor':         ['labor','factori','wag','sweatshop','union','work','overtim','employ','exploit','child','strike'],
        'Ethical':       ['ethic','fair','transpar','right','corrupt','moral','respons','divers','inclus','account','whistlebow'],
        'Politics':      ['polici','legisl','ban','govern','elect','tax','regul','law','congress','polit','campaign','white','biden','trump']
    }
    scores = {topic: sum(1 for s in stems if s in kws) for topic, kws in keyword_sets.items()}
    best = max(scores, key=scores.get)
    return best if scores[best] > 0 else 'General'

analyzer = SentimentIntensityAnalyzer()

def analyze_texts(texts):
    rows = []
    for txt in texts:
        s = analyzer.polarity_scores(txt)['compound']
        t = classify_topic(txt)
        rows.append({'text': txt, 'topic': t, 'sentiment': s})
    return pd.DataFrame(rows)

# Slack alert
def send_slack_alert(brand, lows):
    if SLACK_WEBHOOK_URL:
        msg = f"Alert for {brand}: low sentiment in {', '.join(lows)}"
        requests.post(SLACK_WEBHOOK_URL, json={'text': msg})

# Benchmarking function
TEST_BRANDS = ['Nike','Tesla','Amazon','Puma','Google','Coca-Cola','Adidas','Microsoft','Starbucks','Uber']
def run_benchmark():
    latencies, avg_sents = [], []
    for b in TEST_BRANDS:
        st_time = time.time()
        rp = fetch_reddit_posts(b)
        nh = fetch_news_headlines(b)
        df = analyze_texts(rp + nh)
        latencies.append(time.time() - st_time)
        avg_sents.append(df['sentiment'].mean())
    return pd.DataFrame({'brand': TEST_BRANDS, 'latency_s': latencies, 'avg_sentiment': avg_sents})

# Streamlit UI
st.title("Ethical Brand Sentiment Analyzer")
brand     = st.text_input("Enter brand name:")
threshold = st.slider("Alert threshold:", -1.0, 0.0, -0.3)

if st.button("Analyze"):
    with st.spinner("Fetching & analyzing…"):
        reddit_posts   = fetch_reddit_posts(brand)
        news_headlines = fetch_news_headlines(brand)
        if not reddit_posts and not news_headlines:
            st.warning("No data found. Try another brand.")
        else:
            df = analyze_texts(reddit_posts + news_headlines)

            # Metrics
            st.subheader("Metrics")
            st.write(f"Total Inputs: {len(df)}")
            st.write(f"Avg Sentiment: {df['sentiment'].mean():.3f}")

            # Charts/Visualizations
            st.subheader("Avg Sentiment by Topic")
            fig1, ax1 = plt.subplots()
            df.groupby('topic')['sentiment'].mean().plot.bar(
                ax=ax1, ylim=[-1,1])
            st.pyplot(fig1)

            st.subheader("Topic Distribution")
            fig2, ax2 = plt.subplots()
            df['topic'].value_counts().plot.pie(
                autopct='%1.0f%%', ax=ax2)
            ax2.set_ylabel("")
            st.pyplot(fig2)

            st.subheader("Sentiment Score Distribution")
            fig3, ax3 = plt.subplots()
            df['sentiment'].plot.hist(bins=10, ax=ax3)
            st.pyplot(fig3)

            # Export via CSV
            ts      = datetime.now().strftime('%Y%m%d_%H%M%S')
            fname   = f"{brand}_report_{ts}.csv"

            # showiing filename format
            st.caption(f"Filename format: {brand}_report_YYYYMMDD_HHMMSS.csv")
            st.caption(f"Example: {fname}")

            st.download_button(
                "Download CSV",
                data=df.to_csv(index=False),
                file_name=fname,
                mime="text/csv"
            )

            # Alert functionality
            lows = df.groupby('topic')['sentiment']\
                     .mean()\
                     .loc[lambda x: x < threshold].index.tolist()
            if lows:
                st.error(f"Low sentiment detected in: {', '.join(lows)}")
                send_slack_alert(brand, lows)

if st.button('Run Sample Benchmark'):
    bench = run_benchmark()
    st.subheader('Benchmark Results')
    st.dataframe(bench)
    st.bar_chart(bench.set_index('brand')['latency_s'])
    st.bar_chart(bench.set_index('brand')['avg_sentiment'])
