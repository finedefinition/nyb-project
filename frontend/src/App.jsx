import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Import Routes instead of Switch
import { Header } from './components/Header';
import { Main } from './components/Main';
import { YachtPage } from './components/YachtPage';
import { FullCard } from "./components/FullCard";
import { VesselForm } from "./components/VesselForm";
import { NotFound } from "./components/NotFound";
import { UpdatePage } from "./components/UpdatePage";


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
          <Route path="/update/:id" element={<VesselForm />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </>
    </Router>
  );
};
