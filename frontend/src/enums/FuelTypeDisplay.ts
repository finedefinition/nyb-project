import {FuelType} from "./FuelType";

export class FuelTypeDisplay {
    static getDisplayName(fuelType: FuelType): string {
        switch(fuelType) {
            case FuelType.ALL_FUEL_TYPES:
                return "All fuel types";
            case FuelType.DIESEL:
                return "Diesel";
            case FuelType.ELECTRIC:
                return "Electric";
            case FuelType.PETROL_GASOLINE:
                return "Petrol/Gasoline";
            case FuelType.GAS_LPG:
                return "Gas/LPG";
            case FuelType.HYBRID:
                return "Hybrid";
            default:
                return fuelType;  // defaulting to raw ENUM value
        }
    }
}