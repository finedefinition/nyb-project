import './GoodCard.css';

export const GoodCard = ({good}) => {
    return (
        <div className="GoodCard">
            <img
                src={`/Images/SectionCard/${good.imageUrl}`} // Use a relative path starting with `/` and interpolate `good.imageUrl`
                alt="Boat"
                className="GoodCard__img"
            />
            <h3 className="GoodCard__name">{good.name}</h3>
            <div className="GoodCard__place">{good.place} | {good.year} </div>
            <div className="GoodCard__price"><b>€{good.price}</b></div>
        </div>
    )
}