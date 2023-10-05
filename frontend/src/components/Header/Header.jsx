import './Header.css';

export const Header = () => {
  return (
    <header className="header">
    <div className="header__logo">
      <a href="/" className='logo'>LOGO</a>
    </div>
    <nav className="header__nav">
      <ul className="header__nav__item">
        <li className="header__nav__list">
          <a href="/" className="header__nav__link">Yachts</a>
        </li>
        <li className="header__nav__list">
          <a href="/" className="header__nav__link">How it works?</a>
        </li>
        <li className="header__nav__list">
        <a href="/" className="header__nav__link">Services</a>
        </li>
        <li className="header__nav__list">
          <a href="/" className="header__nav__link">About us</a>
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