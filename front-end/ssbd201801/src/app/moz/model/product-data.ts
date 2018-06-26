import {CategoryData} from './category-data';
import {UnitData} from './unit-data';
import {OwnerData} from './owner-data';

export interface ProductData {
  id?: number;
  name?: string;
  description?: string;
  price?: number;
  qty?: number;
  active?: boolean;
  category?: CategoryData;
  unit?: UnitData;
  owner?: OwnerData;
  unitId?: number;
  categoryId?: number;
}
