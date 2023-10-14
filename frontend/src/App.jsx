import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Import Routes instead of Switch
import { Header } from './components/Header';
import { Main } from './components/Main';
import { YachtPage } from './components/YachtPage';
import { FullCard } from "./components/FullCard";
import { VesselForm } from "./components/VesselForm";
import { NotFound } from "./components/NotFound";


export const App = () => {
  return (
    <Router>
      <>
        <Header />
        <Routes> {/* Use Routes instead of Switch */}
          <Route path="/" element={<Main />} />
          <Route path="/yachts" element={<YachtPage />} />
          <Route path="/full-card/:id" element={<FullCard />} />
          <Route path="/form" element={<VesselForm />} />
          {/* Add a catch-all route for unmatched routes */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </>
    </Router>
  );
};
