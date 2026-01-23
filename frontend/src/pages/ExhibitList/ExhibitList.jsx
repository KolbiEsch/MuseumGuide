import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../../api/axiosConfig';
import styles from './ExhibitList.module.scss';

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
        <div className={styles.container}>
            <h1 className={styles.header}>Museum Exhibits</h1>

            <div className={styles.grid}>
                {exhibits.map((exhibit) => (
                   <Link className={styles.card}
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