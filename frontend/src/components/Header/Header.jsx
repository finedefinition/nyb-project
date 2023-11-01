import './Header.css';
import {Link} from "react-router-dom";
import {useState} from "react";

export const Header = () => {
  const [isNavbarCollapsed, setNavbarCollapsed] = useState(true);

  const toggleNavbar = () => {
    setNavbarCollapsed(!isNavbarCollapsed);
  };
  function handleClick(event, linkPath) {
    // If the current location's pathname matches the link's destination, then reload
    if (window.location.pathname === linkPath) {
      event.preventDefault();
      window.location.reload();
    }
  }

  return (
    <header>
      <nav className="navbar navbar-expand-lg">
        <div className="container-fluid">
          <button className="navbar-toggler" type="button" aria-label="Toggle navigation" id="navbarBtnHamburger" onClick={toggleNavbar}>
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className={`collapse navbar-collapse ${isNavbarCollapsed ? '' : 'show'}`} id="navbarSupportedContent">
            <ul className="navbar-nav header_navbar mb-2 mb-lg-0">
              <li className="nav-item">
                <Link to="/yachts" className="nav-link active" onClick={(e) => handleClick(e, "/yachts")} >Yachts</Link>
              </li>
              <li className="nav-item">
                <Link to="/" className="nav-link" onClick={handleClick} >How it works?</Link>
              </li>
              <li className="nav-item name_company">
                <Link to="/" className="nav-link">
                  <img src="/Images/Logo/logo.svg" alt="Logo Norse Yacht Co."/>
                  <span>Norse Yacht Co.</span>
                </Link>
              </li>
              <li className="nav-item">
                <Link to="/" className="nav-link" onClick={handleClick} >About us</Link>
              </li>
              <li className="nav-item">
                <Link to="/form" className="nav-link" onClick={(e) => handleClick(e, "/form")} >Form</Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
        {/*<div className="header__buttons">*/}
        {/*    <button className="header__btn">Sign In</button>*/}
        {/*    <button className="header__btn">Sign Up</button>*/}
        {/*</div>*/}
  </header>
  )
}
