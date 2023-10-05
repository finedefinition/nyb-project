import { GoodCard } from '../GoodCard/GoodCard';
import './GoodList.css';

export const GoodList = ({ goods }) => {
  return (
    <div className="GoodList">
      {goods.map((good) => (
        <GoodCard good={good} key={good.id} />
      ))}
    </div>
  );
};
