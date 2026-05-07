'use client';

import { useMemo, useState } from 'react';
import { demoChat, demoEvents, demoMembers } from '@/lib/demoData';

const duplicateRisk = (members) => members.filter((m, i) =>
  members.findIndex((n) => n.id !== m.id && (n.phone === m.phone || n.email === m.email || n.name === m.name)) !== -1 && i >= 0
);

export default function Page() {
  const [events] = useState(demoEvents);
  const [members, setMembers] = useState(demoMembers);
  const [query, setQuery] = useState('');
  const [checkInEvent, setCheckInEvent] = useState(2);
  const [chat, setChat] = useState(demoChat);

  const filtered = members.filter((m) => `${m.name} ${m.email} ${m.phone}`.toLowerCase().includes(query.toLowerCase()));
  const dupes = useMemo(() => duplicateRisk(members), [members]);
  const totalAttendance = members.reduce((acc, m) => acc + m.attended.length, 0);
  const leaderboard = [...members].sort((a, b) => b.points - a.points);

  const checkIn = (id) => setMembers((prev) => prev.map((m) => m.id === id && !m.attended.includes(checkInEvent)
    ? { ...m, attended: [...m.attended, checkInEvent], points: m.points + 10 }
    : m));

  return (
    <main>
      <h1>Play Cousins Collective MVP</h1>
      <p className="small">Warm, low-burden event + family engagement workspace.</p>

      <section className="grid grid-2">
        <div className="card"><b>Total members</b><div>{members.length}</div></div>
        <div className="card"><b>Duplicate-risk records</b><div>{dupes.length}</div></div>
        <div className="card"><b>Total event attendance</b><div>{totalAttendance}</div></div>
        <div className="card"><b>Most engaged family</b><div>{leaderboard[0]?.name}</div></div>
      </section>

      <section className="card">
        <h2>Public Event List</h2>
        <div className="grid grid-2">
          {events.map((e) => <article key={e.id} className="card">
            <div className="row"><h3>{e.title}</h3><span className="badge">{e.status}</span></div>
            <div>{e.date} • {e.time}</div><div>{e.location}</div>
            <div className="small">{e.category} | Capacity: {e.capacity}</div>
            <p>{e.description}</p>
          </article>)}
        </div>
      </section>

      <section className="card">
        <h2>Staff Check-in</h2>
        <div className="row">
          <input placeholder="Search name, email, phone" value={query} onChange={(e) => setQuery(e.target.value)} />
          <select value={checkInEvent} onChange={(e) => setCheckInEvent(Number(e.target.value))}>
            {events.map((e) => <option key={e.id} value={e.id}>{e.title}</option>)}
          </select>
        </div>
        {filtered.map((m) => <div key={m.id} className="row" style={{ justifyContent: 'space-between', marginTop: 10 }}>
          <div><b>{m.name}</b> <span className="small">({m.email})</span></div>
          <button onClick={() => checkIn(m.id)}>One-click check-in</button>
        </div>)}
      </section>

      <section className="grid grid-2">
        <div className="card">
          <h2>Family Profiles + Engagement</h2>
          {members.map((m) => <div key={m.id} style={{ marginBottom: 10 }}>
            <b>{m.name}</b> • {m.phone}<br />
            <span className="small">Household: {m.household.join(', ')} | Preferred: {m.preferred} | Events: {m.attended.length}</span>
          </div>)}
        </div>
        <div className="card">
          <h2>Community Features (MVP Simulated)</h2>
          <p className="small">Friends attending = opt-in only, simple chat, points, poll + leaderboard.</p>
          <h3>Event Chat</h3>
          {chat.filter((c) => c.eventId === 2).map((c, i) => <div key={i}><b>{c.user}:</b> {c.message}</div>)}
          <button onClick={() => setChat((p) => [...p, { eventId: 2, user: 'Staff', message: 'See you all at 11!' }])}>Post staff update</button>
          <h3>Leaderboard</h3>
          {leaderboard.map((m, i) => <div key={m.id}>{i + 1}. {m.name} — {m.points} pts</div>)}
        </div>
      </section>
    </main>
  );
}
