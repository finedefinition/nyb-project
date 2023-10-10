import './Catalog.css';
import goods from '../SectionCard/goods.json';
import { GoodCard } from '../GoodCard';

export const Catalog = () => {
  return (
    <div className="catalog">
      { goods.map(good => (
        <div className="card">
          <GoodCard good={good}/>
        </div>
      ))

      }
    </div>
  )
}