export const demoEvents = [
  { id: 1, title: 'Healing Circle Night', date: '2026-05-16', time: '6:00 PM', location: 'West End Community Hall', category: 'Caregiver Support', capacity: 40, status: 'Upcoming', description: 'Trauma-informed support circle with light dinner.' },
  { id: 2, title: 'Saturday Play Lab', date: '2026-05-10', time: '11:00 AM', location: 'Shawnee Park', category: 'Youth Enrichment', capacity: 60, status: 'Current', description: 'Family games, art stations, and cousin connections.' },
  { id: 3, title: 'Backyard STEM Pop-up', date: '2026-04-20', time: '4:30 PM', location: 'Portland Learning Hub', category: 'Community Program', capacity: 35, status: 'Past', description: 'Hands-on mini STEM projects for ages 7-13.' }
];

export const demoMembers = [
  { id: 1, name: 'Avery Johnson', email: 'avery@email.com', phone: '502-111-2222', household: ['Avery', 'Mia(8)'], interests: ['Art', 'Reading'], preferred: 'Text', optInFriends: true, points: 85, attended: [2, 3] },
  { id: 2, name: 'Jordan Lee', email: 'jordan@email.com', phone: '502-999-3333', household: ['Jordan', 'Noah(11)', 'Skye(5)'], interests: ['Sports'], preferred: 'Email', optInFriends: false, points: 60, attended: [3] },
  { id: 3, name: 'Avery Johnson', email: 'avery.j@email.com', phone: '502-111-2222', household: ['Avery'], interests: ['Art'], preferred: 'Text', optInFriends: true, points: 15, attended: [] }
];

export const demoChat = [
  { eventId: 2, user: 'Avery', message: 'We can bring extra crayons!' },
  { eventId: 2, user: 'Jordan', message: 'Anyone else coming from Portland?' }
];
