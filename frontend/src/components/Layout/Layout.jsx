import { Header } from '../Header';
import { Outlet } from 'react-router-dom';
import './Layout.css';

export const Layout = () => {
  return (
    <>
    <header>
      <Header />
    </header>
  
    <main className='container'>
      <Outlet />
    </main>

    <footer>

    </footer>

    </>
  )
}