package utry.util.convert;
/**
 * ���������͵��������ʽ
 * @author DFF
 *
 */
public class DataTypes {
	
	public static final int DATATABLEOBJECT = 0;
	public static final int DATATABLESTRING = 1;
	public static final int DATATABLEBOOLEAN = 2;
	public static final int DATATABLESHORT = 3;
	public static final int DATATABLEINT = 4;
	public static final int DATATABLELONG = 5;
	public static final int DATATABLEFLOAT = 6;
	public static final int DATATABLEDOUBLE = 7;
	public static final int DATATABLEDATE = 8;
	public static final int DATATABLETIME = 9;
	public static final int DATATABLETIMESTAMP = 10;
	public static final int DATATABLEBYTE = 11;
	public static final int DATATABLEBYTES = 12;
	public static final int DATATABLEBIGDECIMAL = 13;
	//�������͵�����
	public static final String[] DATATABLETYPENAMES = { "OBJECT", 
			"STRING", "BOOLEAN", "SHORT", "INTEGER", "LONG", "FLOAT", "DOUBLE", 
			"DATE", "TIME", "TIMESTAMPLE", "BYTE", "BYTES", "BIGDECIMAL" };
	 //����������͵Ĵ����ԣ�����������
	public static boolean checkDataType(int dataType){
	    return (dataType > -1) && (dataType < 14);
	}	  
	//����������͵����ʹ�
	public static String getDataTypeName(int dataType){
		if (checkDataType(dataType)) {
			return DATATABLETYPENAMES[dataType];
		}
			return null;
	}
	/**
	 * ��������������������������͵�������ʽ
	 * @param dataTypeName
	 * @return
	 */
	public static int getDataType(String dataTypeName){
		int type=-1;
		for(int i=0;i<DATATABLETYPENAMES.length;i++){
			if(DATATABLETYPENAMES[i].equals(dataTypeName.toUpperCase())){
				type=i;
				break;
			}
		}
		return type;
	}
	/**
	 * ������ݶ�������͵����ֱ����ʽ��-1��ʾ������������
	 * @param ob
	 * @return
	 */
	public static int getOjectDataType(Object ob){
		String obStr=ob.getClass().getName();
		String typeName=obStr.substring(obStr.lastIndexOf(".")+1).trim().toUpperCase();
		return getDataType(typeName);
		
	}
}
