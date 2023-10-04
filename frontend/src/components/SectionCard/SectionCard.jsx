import { GoodList } from '../GoodList';
import './SectionCard.css';
import goods from './goods.json';

export const SectionCard = () => {
  return (
    <section className="section">
    <div className="section__up">
      <h2 className='section__title'> Вибери свою яхту</h2>
      <button className='section__btn'>Побачити більше</button>
    </div>
      <GoodList goods={goods}/>
    </section>
  )
}