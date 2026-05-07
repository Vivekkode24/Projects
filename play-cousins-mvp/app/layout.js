import './globals.css';

export const metadata = {
  title: 'Play Cousins Collective MVP',
  description: 'Hackathon MVP for family-focused events and engagement.'
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}
