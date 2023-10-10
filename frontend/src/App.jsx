import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Import Routes instead of Switch
import { Header } from './components/Header';
import { Main } from './components/Main';
import { YachtPage } from './components/YachtPage';


export const App = () => {
  return (
    <Router>
      <>
        <Header />
        <Routes> {/* Use Routes instead of Switch */}
          <Route path="/" element={<Main />} /> {/* Use element prop */}
          <Route path="/yachts" element={<YachtPage />} /> {/* Use element prop */}
        </Routes>
      </>
    </Router>
  );
};
