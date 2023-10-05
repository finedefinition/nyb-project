import './GoodCard.css';

export const GoodCard = ({ good }) => {
  return (
    <div className="GoodCard">
          <img src="/src/Images/GoodCard/yacht.jpg" alt="Boat" className="GoodCard__img" />
          <h3 className="GoodCard__name">{good.name}</h3>
          <div className="GoodCard__price">${good.price}</div>
          <div className="GoodCard__place">{good.place}</div>
    </div>
  )
}