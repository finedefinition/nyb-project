import {KeelType} from "./KeelType";

export class KeelTypeDisplay {
    static getDisplayName(keelType: KeelType): string {
        switch (keelType) {
            case KeelType.ALL_KEEL_TYPES:
                return "All keel types";
            case KeelType.BILGE_KEEL:
                return "Bilge/Twin Keel";
            case KeelType.BULB_KEEL:
                return "Bulb Keel";
            case KeelType.CANTING_KEEL:
                return "Canting Keel";
            case KeelType.CENTREBOARD:
                return "Centreboard & Daggerboard";
            case KeelType.FIN_KEEL:
                return "Fin Keel";
            case KeelType.FULL_KEEL:
                return "Full Keel";
            case KeelType.LIFTING_KEEL:
                return "Lifting & Swing Keel";
            case KeelType.LONG_KEEL:
                return "Long Keel";
            case KeelType.SHOAL_KEEL:
                return "Shoal Keel";
            case KeelType.WINGED_KEEL:
                return "Winged Keel";
            default:
                return keelType;
        }
    }
}