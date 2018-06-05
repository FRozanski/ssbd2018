import { forEach } from "@angular/router/src/utils/collection";

export class SelectValues {

    public static readonly roleSelectValues: SelectValue[] = [
        {
            value: "user",
            displayedValue: "ACCOUNT.USER"
        },
        {
            value: "manager",
            displayedValue: "ACCOUNT.MANAGER"
        },
        {
            value: "admin",
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