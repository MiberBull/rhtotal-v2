
export class MathPrecision {

	/**
	 * @function round
	 * @param number is the number with decimals to be round
	 * @param precision is the number in representation of total decimals to show the number
	 * @retrun numberRounded the finally number obtaind from round precision
	 */
	static round(number, precision) {
		let factor = Math.pow(10, precision);
		let tempNumber = number * factor;
		let roundedTempNumber = Math.round(tempNumber);
		let numberRounded = roundedTempNumber / factor

		return numberRounded;
	}
}

