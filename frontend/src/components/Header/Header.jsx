import './Header.css';
import {Link} from "react-router-dom";

export const Header = () => {
  function handleClick(event, linkPath) {
    // If the current location's pathname matches the link's destination, then reload
    if (window.location.pathname === linkPath) {
      event.preventDefault();
      window.location.reload();
    }
  }

  return (
    <header className="header">
      <div className="header__logo">
        <Link to="/" className='logo'>LOGO</Link>
      </div>
    <nav className="header__nav">
      <ul className="header__nav__item">
        <li className="header__nav__list">
          <Link to="/yachts" className="header__nav__link" onClick={(e) => handleClick(e, "/yachts")} >Yachts</Link>
        </li>
        <li className="header__nav__list">
         <Link to="/" className="header__nav__link" onClick={handleClick} >How it works?</Link>
        </li>
        <li className="header__nav__list">
          <Link to="/" className="header__nav__link" onClick={handleClick} >About us</Link>
        </li>
        <li className="header__nav__list">
          <Link to="/form" className="header__nav__link" onClick={(e) => handleClick(e, "/form")} >Form</Link>
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