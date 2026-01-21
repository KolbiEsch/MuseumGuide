import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axiosConfig';

const ExhibitList = () => {
    const [exhibits, setExhibits] = useState([]);

    useEffect(() => {
        const fetchExhibits = async () => {
            try {
                const response = await api.get('/api/exhibits');
                setExhibits(response.data);
            } catch (error) {
                console.error("Failed to fetch exhibits", error);
            }
        };

        fetchExhibits();
    }, []);

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