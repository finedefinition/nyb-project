import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Yachts } from './pages/Yachts';
import { Main } from './pages/Main';
import { Layout } from './components/Layout';

export const App = () => {
  return (
    <>
    <Router>
        <Routes>
          <Route path='/' element={<Layout />}>
            <Route exact index element={<Main />}/>
            <Route exact path="yachts" element={<Yachts />}/>
          </Route>
        </Routes>
      </Router>
    </>
  )
}