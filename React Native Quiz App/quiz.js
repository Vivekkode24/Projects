import React from 'react';
import {StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import { useEffect, useState } from "react";
import { Touchable } from 'react-native';



const shuffleArray=(array)=>{
    for (let i = array.length -1;i>0;i--){
        const j = Math.floor(Math.random()*(i+1));
        [array[i], array[j]] =[array[j], array[i]];
    }
}


const Quiz = ({navigation}) => {
    const [questions, setQuestions] = useState();
    const [ques, setQues]= useState(0);
    const [options, setOptions]=useState([])
    const [score, setScore]= useState(0)
    const [isLoading, setIsLoading]=useState(false)

    const getQuiz=async()=>{
        setIsLoading(true)
        const url='https://opentdb.com/api.php?amount=10&category=18&type=multiple&encode=url3986';
        const res = await fetch(url);
        const data= await res.json();
        setQuestions(data.results);
        setOptions(generateOptionsAndShuffle(data.results[0]))
        setIsLoading(false)
    };

    useEffect(()=>{
        getQuiz();
    },[]);

    const handleNextPress=()=>{
        setQues(ques+1)
        setOptions(generateOptionsAndShuffle(questions[ques+1]))
    }

    const generateOptionsAndShuffle=(_question)=>{
        const options=[..._question.incorrect_answers]
        options.push(_question.correct_answer)
        // console.log(options, "Before")
        shuffleArray(options)
        // console.log(options, "After")

        return options
    }

    const handleSelectedOption=(_option)=>{
        if(_option===questions[ques].correct_answer){
            setScore(score+10)
        }
        if(ques!==9){
            setQues(ques+1)
        setOptions(generateOptionsAndShuffle(questions[ques+1]))
        }
        if(ques===9){
            handleShowResult()
        }
    }
    
    const handleShowResult=()=>{
        navigation.navigate('Result',{score: score})
    }

    return(
        <View style={styles.container}>
            {isLoading?<View style={{display:'flex', justifyContent:'center',alignItems:'center'}}><Text style={{fontSize:32, fontWeight:'700'}}>Loading...</Text></View> : questions&&(
            <View style={styles.parent}>
                <View style={styles.top}>
                    <Text style={styles.questions}>Q.{decodeURIComponent(questions[ques].question)} </Text>
                </View>
                <View style={styles.options}>
                    <TouchableOpacity style={styles.optionbutton} onPress={()=>handleSelectedOption(options[0])}>
                        <Text style={styles.optiontext}>{decodeURIComponent(options[0])}</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.optionbutton} onPress={()=>handleSelectedOption(options[1])}>
                        <Text style={styles.optiontext}>{decodeURIComponent(options[1])}</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.optionbutton} onPress={()=>handleSelectedOption(options[2])}>
                        <Text style={styles.optiontext}>{decodeURIComponent(options[2])}</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.optionbutton} onPress={()=>handleSelectedOption(options[3])}>
                        <Text style={styles.optiontext}>{decodeURIComponent(options[3])}</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.bottom}>
                    {/* <TouchableOpacity style={styles.buttons}>
                        <Text style={styles.buttontext}>SKIP</Text>
                    </TouchableOpacity> */}
                    {ques!==9&&<TouchableOpacity style={styles.buttons} onPress={handleNextPress}>
                        <Text style={styles.buttontext}>SKIP</Text>
                    </TouchableOpacity>}
                    {ques===9&&<TouchableOpacity style={styles.buttons} onPress={handleShowResult}>
                        <Text style={styles.buttontext}>Finish</Text>
                    </TouchableOpacity>}
                    
                </View>
            </View>
            )}
        </View>
    );
};

export default Quiz;

const styles = StyleSheet.create({
    container:{
        paddingTop:40,
        paddingHorizontal:20,
        height:'100%',
    },
    top:{
        marginVertical:16
    },
    options:{
        marginVertical:16,
        flex:1.
    },
    bottom:{
        marginBottom:12,
        paddingVertical:16,
        justifyContent:"space-between",
        flexDirection:'row',
    },
    buttons:{
        backgroundColor:'#1A759F',
        padding:12,
        paddingVertical:16,
        borderRadius:16,
        alignItems:'center',
        marginBottom:30,
    },
    buttontext:{
        fontSize:18,
        fontWeight:'600',
        color:'white',
    },
    questions:{
        fontSize:26,
    },
    optiontext:{
        fontSize:16,
        fontWeight:'500',
        color:'white',
    },
    optionbutton:{
        paddingVertical:12,
        marginVertical:6,
        backgroundColor:'#1FA234',
        paddingHorizontal:12,
        borderRadius:12,
    },
    parent:{
        height:'100%'
    }
});