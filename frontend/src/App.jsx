import { useEffect, useState } from 'react'
import api from './api/axiosConfig'

function App() {
  const [connectionStatus, setConnectionStatus] = useState('Checking connection...');

  useEffect(() => {
    const fetchExhibits = async () => {
        try {
            console.log('Attempting to fetch /api/exhibits...');

            const response = await api.get('api/exhibits')

            console.log('API Response:', response);
            console.log('Exhibits Data:', response.data);

            setConnectionStatus(`Success! Found ${response.data.length} exhibits`);
        } catch (error) {
            console.error('API Error:', error);
            setConnectionStatus(`Error: ${error.message}`);
        }
    };

    fetchExhibits();
  }, []);

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif'}}>
        <h1>Museum Guide Frontend</h1>
        <div style={{
            padding: '15px',
            backgroundColor: connectionStatus.includes('Error') ? '#ffcccc' : '#ccffcc',
            borderRadius: '5px'
        }}>
            <strong>Status:</strong> {connectionStatus}
        </div>
    </div>
  );
}

export default App