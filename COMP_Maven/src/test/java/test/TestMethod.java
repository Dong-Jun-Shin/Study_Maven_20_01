package test;

import org.junit.Test;

public class TestMethod {
	@Test
	public void testSplit() {
		String str = "난, 쉼표,로 구분이, 다, 되고, 있어";
		String num = "1+5+4+5+6+7+4"; 
		String numSpace = "1 + 5+ 4 +5 +6+ 7 + 4";
		
		String[] strArr = str.split(",");
		String[] numArr = num.split("\\"
				+ "+");
		String[] numSpaceArr = numSpace.split("\\+");
		
		for(String sortStr : strArr) {
			System.out.println(sortStr);
		}
		
		for(String sortStr : numArr) {
			System.out.println(sortStr);
		}
		
		for(String sortStr : numSpaceArr) {
			System.out.println(sortStr.replace(" ", ""));
		}
	}
}
