import { Routes, Route } from 'react-router-dom';
import ExhibitList from '../pages/ExhibitList';
import ExhibitDetail from '../pages/ExhibitDetail';

const AppRoutes = () => {
  return (
    <Routes>
      {/* Route for the Exhibit List (Home) */}
      <Route path="/" element={<ExhibitList />} />

      {/* Route for Exhibit Details */}
      <Route path="/exhibit/:id" element={<ExhibitDetail />} />
    </Routes>
  );
};

export default AppRoutes;