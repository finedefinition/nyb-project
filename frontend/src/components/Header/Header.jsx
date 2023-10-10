import './Header.css';
import {Link, NavLink} from "react-router-dom";


// import {useLocation} from 'react-router-dom';

export const Header = () => {
  // let url = useLocation();
  return (
    <header className="header">
    <div className="header__logo">
      <Link to="/" className='logo'>LOGO</Link>
    </div>
    <nav className="header__nav">
      <ul className="header__nav__item">
        <li className="header__nav__list">
        <NavLink to="/yachts" className ="header__nav__link">Yachts</NavLink>
        </li>
        <li className="header__nav__list">
          <a href="/" className="header__nav__link">How it works?</a>
        </li>
        <li className="header__nav__list">
        <a href="https://finedefinition.github.io/nyb-project/" className="header__nav__link">Services</a>
        </li>
        <li className="header__nav__list">
          <a href="https://finedefinition.github.io/nyb-project/" className="header__nav__link">About us</a>
        </li>
      </ul>
    </nav>
    <div className="header__buttons">
      <button className="header__btn">Sign In</button>
      <button className="header__btn">Sign Up</button>
    </div>
  </header>
  )
}