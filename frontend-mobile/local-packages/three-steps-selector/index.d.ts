import { EventEmitter } from '@angular/core';
import { ControlValueAccessor } from '@angular/forms';

export declare class ThreeStepsSelectorComponent implements ControlValueAccessor {
    disabled: boolean;
    onchange: EventEmitter<any>;
    value: number;
    writeValue(val: any): void;
    registerOnChange(fn: any): void;
    registerOnTouched(fn: any): void;
    setDisabledState(isDisabled: boolean): void;
    select(step: number): void;
}

export declare class ThreeStepsSelectorModule {}
