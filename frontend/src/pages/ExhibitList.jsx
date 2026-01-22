import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axiosConfig';

const ExhibitList = () => {
    const [exhibits, setExhibits] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchExhibits = async () => {
            try {
                const response = await api.get('/api/exhibits');
                setExhibits(response.data);
                setError(null);
            } catch (error) {
                console.error("Failed to fetch exhibits", error);
                setError("Unable to load exhibits. Please try again");
            } finally {
                setLoading(false);
            }
        };

        fetchExhibits();
    }, []);

    if (loading) return <div>Loading exhibits...</div>
    if (error) return <div>{error}</div>

    return (
        <div>
            <h1>Museum Exhibits</h1>

            <div>
                {exhibits.map((exhibit) => (
                   <Link
                     to={`/exhibit/${exhibit.id}`}
                     key={exhibit.id}
                   >
                       <div>
                           {/* Thumbnail placeholder */}
                           <div>

                           </div>

                           <div>
                               <h3>{exhibit.name}</h3>
                               <p>{exhibit.location}</p>
                           </div>
                       </div>
                   </Link>
                ))}
            </div>
        </div>
    );
};

export default ExhibitList;