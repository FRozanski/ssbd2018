import { forEach } from "@angular/router/src/utils/collection";

export class SelectValues {

    public static readonly roleSelectValues: SelectValue[] = [
        {
            value: "USER",
            displayedValue: "ACCOUNT.USER"
        },
        {
            value: "MANAGER",
            displayedValue: "ACCOUNT.MANAGER"
        },
        {
            value: "ADMIN",
            displayedValue: "ACCOUNT.ADMIN"
        }
    ];

    
    fetchValues(selectValues: SelectValue[]) {
        let arr: string[] = [];
        for(let i=0; i<selectValues.length; i++) {
            arr.push(selectValues[i].value);
        }
        return arr;

    }
}

export class SelectValue {
    value: string;
    displayedValue: string;
}