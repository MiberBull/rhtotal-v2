"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var ThreeStepsSelectorComponent = /** @class */ (function () {
    function ThreeStepsSelectorComponent() {
        this.disabled = false;
        this.onchange = new core_1.EventEmitter();
        this.value = 0;
        this.onChange = function () {};
        this.onTouched = function () {};
    }
    ThreeStepsSelectorComponent.prototype.writeValue = function (val) {
        this.value = val || 0;
    };
    ThreeStepsSelectorComponent.prototype.registerOnChange = function (fn) {
        this.onChange = fn;
    };
    ThreeStepsSelectorComponent.prototype.registerOnTouched = function (fn) {
        this.onTouched = fn;
    };
    ThreeStepsSelectorComponent.prototype.setDisabledState = function (isDisabled) {
        this.disabled = isDisabled;
    };
    ThreeStepsSelectorComponent.prototype.select = function (step) {
        if (this.disabled) return;
        this.value = step;
        this.onChange(step);
        this.onTouched();
        this.onchange.emit(step);
    };
    __decorate([
        core_1.Input()
    ], ThreeStepsSelectorComponent.prototype, "disabled", void 0);
    __decorate([
        core_1.Output()
    ], ThreeStepsSelectorComponent.prototype, "onchange", void 0);
    ThreeStepsSelectorComponent = __decorate([
        core_1.Component({
            selector: 'tm-three-steps-selector',
            template: '<div style="display:flex;gap:4px"><button *ngFor="let s of [1,2,3]" (click)="select(s)" [style.background]="value===s?\'#1099d6\':\'#ccc\'" style="border:none;border-radius:50%;width:28px;height:28px;color:#fff;cursor:pointer">{{s}}</button></div>',
            providers: [{
                provide: forms_1.NG_VALUE_ACCESSOR,
                useExisting: core_1.forwardRef(function () { return ThreeStepsSelectorComponent; }),
                multi: true
            }]
        })
    ], ThreeStepsSelectorComponent);
    return ThreeStepsSelectorComponent;
}());
exports.ThreeStepsSelectorComponent = ThreeStepsSelectorComponent;

var common_1 = require("@angular/common");
var ThreeStepsSelectorModule = /** @class */ (function () {
    function ThreeStepsSelectorModule() {}
    ThreeStepsSelectorModule = __decorate([
        core_1.NgModule({
            imports: [common_1.CommonModule, forms_1.FormsModule],
            declarations: [ThreeStepsSelectorComponent],
            exports: [ThreeStepsSelectorComponent]
        })
    ], ThreeStepsSelectorModule);
    return ThreeStepsSelectorModule;
}());
exports.ThreeStepsSelectorModule = ThreeStepsSelectorModule;
