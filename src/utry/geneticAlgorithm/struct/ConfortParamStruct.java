package utry.geneticAlgorithm.struct;

import utry.util.datatable.DataTable;

public class ConfortParamStruct {
	//
	public int nrestMin;
	public int nrestMax;
	public int minContinueWork;
	public int maxContinueWork;
	public float minWorkHour;
	public float maxWorkHour;
	public int weekendRestDaysBanlance;
	public int betweenRestDaysBanlance;
	public String restNotPointString;
	public int continueRest;
	public int continueRestNum;
	public int classTimeInterval;
	public String lunchBeginPointString;
	public String lunchEndPointString;
	public String dinnerBeginPointString;
	public String dinnerEndPointString;
	public int dinnerTimeout;
	public float dinnerTimePoint;
	public int lunchBeginPointInt;
	public int lunchEndPointInt;
	public int dinnerBeginPointInt;
	public int dinnerEndPointInt;
	public int restNotPointInt;
	public String restWorkRange;
	public DataTable maxMinContinueWorkRange;
	public ConfortParamStruct(String restRange){
		nrestMin=0;
		nrestMax=0;
		minContinueWork=0;
		maxContinueWork=0;
		minWorkHour=0;
		maxWorkHour=0;
		weekendRestDaysBanlance=0;
		betweenRestDaysBanlance=0;
		restNotPointString="";
		continueRestNum=0;
		classTimeInterval=0;
		lunchBeginPointString="";
		lunchEndPointString="";
		dinnerBeginPointString="";
		dinnerEndPointString="";
		dinnerTimeout=0;
		dinnerTimePoint=0;
		lunchBeginPointInt=0;
		lunchEndPointInt=0;
		dinnerBeginPointInt=0;
		dinnerEndPointInt=0;
		restNotPointInt=0;
		restWorkRange=restRange;
		maxMinContinueWorkRange=new DataTable();
	}
	
	
}
