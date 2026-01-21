import { useParams, Link } from 'react-router-dom';

const ExhibitDetail = () => {
    const { id } = useParams();

    return (
        <div style={{ padding: '20px'}}>
            <h1>Exhibit Detail</h1>
            <p>Viewing details for Exhibit with ID: {id}</p>
            <Link to="/">Back to List</Link>
        </div>
    );
};

export default ExhibitDetail;