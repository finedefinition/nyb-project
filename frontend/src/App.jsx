import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { Header } from './components/Header';
import { Main } from './pages/Main';
import { YachtPage } from './components/YachtPage';
import { FullCard } from "./components/FullCard";
import { CreateForm } from "./components/CreateForm";
import { UpdateForm } from "./components/UpdateForm";
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
          <Route path="/form" element={<CreateForm />} />
          <Route path="/update/:id" element={<UpdateForm />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </>
    </Router>
  );
};
