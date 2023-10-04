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
          <a href="/" className="header__nav__link"> Про нас</a>
        </li>
        <li className="header__nav__list">
          <a href="/" className="header__nav__link">Каталог</a>
        </li>
        <li className="header__nav__list">
        <a href="/" className="header__nav__link">Етапи</a>
        </li>
        <li className="header__nav__list">
          <a href="/" className="header__nav__link">Контакти</a>
        </li>
      </ul>
    </nav>
    <div className="header__buttons">
      <button className="header__btn">Вхід</button>
      <button className="header__btn">Реєстрація</button>
    </div>
  </header>
  )
}