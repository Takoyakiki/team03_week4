package com.team03.week04;

public class TotalRate {
	private double totalRate = 0;
	private double lineCost = 0;
	private double usedCost = 0;
	String resultofLines="",resultofUsed="";
	// 기본요금+회선에 대한 추가요금(요금제에 따라서 기본요금과 추가되는 요금이 다르다)
	public double calNumberOfLine(User user, PlanType planType) {
		int numberOfLines = user.getNumberOfLines();
		lineCost = planType.getBasicMonthlyRate();
		if (numberOfLines > 1 && numberOfLines <= 3) {
			lineCost += ((numberOfLines - 1) * planType.getAdditionalLineRate());
			resultofLines+=(planType.getBasicMonthlyRate()+" + ( "+numberOfLines+" * "+planType.getAdditionalLineRate()+" )");
		} else if (numberOfLines >= 4) {
			lineCost += (((numberOfLines - 3) * planType.getFamilyDistcount())+planType.getAdditionalLineRate()*2);
			resultofLines+=(planType.getBasicMonthlyRate()+" + ( "+2+" * "+planType.getAdditionalLineRate()+" ) + "+
			" ( "+(numberOfLines-3)+" * "+planType.getFamilyDistcount()+" )");
		}
		return lineCost;
	}

	// 초과 금액에 대한 추가요금 계산
	public double calMinuteUsed(User user, PlanType planType) {
		
		int minutesUsed = user.getMinutesUsed();
		double ratePerExcessMinute = planType.getRatePerExcessMinute();
		int includedMinutes = planType.getIncludedMinutes();
		
		if (minutesUsed > includedMinutes) {
			usedCost = (minutesUsed - includedMinutes) * ratePerExcessMinute;
			resultofUsed+=(" + ( "+(minutesUsed - includedMinutes)+" * "+ratePerExcessMinute+" ) = ");
		} else {
			resultofUsed+=" = ";
			usedCost = 0;
		}
		return usedCost;
	}

	public double calTotalRate(User user, PlanType planType) {
		totalRate = calNumberOfLine(user, planType) + calMinuteUsed(user, planType);
		totalRate = Double.parseDouble(String.format("%.2f", totalRate));
		App.LOG.info(resultofLines+resultofUsed+totalRate+"$");
		return totalRate;
	}

	public double getTotalRate() {
		
		return this.totalRate;
	}
}
