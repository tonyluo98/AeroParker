import java.util.regex.Pattern; 

public class Validate {
	
	/**
	 * 
	 * @param email
	 * @return
	 * 
	 * Check if valid email
	 */
	public static boolean validateEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$"; 
		Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        else if (email.length() >255)
        	return false;
        return pat.matcher(email).matches(); 
	}
	
	/**
	 * 
	 * @param str
	 * @param len
	 * @return
	 * 
	 * Check if valid string
	 */
	public static boolean validateString(String str,int len)
	{
		if (str.length() >len)
			return false;
		return true;
	}
	
	/**
	 * 
	 * @param city
	 * @return
	 * 
	 * Check if valid city
	 */
	public static boolean validateCity(String city)
	{
		if (city.length() >255)
			return false;
		return city.matches("[a-zA-Z]+");
	}
	
	
	/**
	 * 
	 * @param postcode
	 * @return
	 * 
	 * Check if valid postcode
	 */
	public static boolean validatePostcode(String postcode)
	{
		String postcodeRegex = "^([A-PR-UWYZ](([0-9](([0-9]"+
				"|[A-HJKSTUW])?)?)|([A-HK-Y][0-9]([0-9]" +
				"|[ABEHMNPRVWXY])?)) ?[0-9][ABD-HJLNP-UW-Z]{2})$";
		if (postcode.length() >10)
			return false;
		Pattern pat = Pattern.compile(postcodeRegex); 
		return pat.matcher(postcode).matches();
	}
	
	
	/**
	 * 
	 * @param number
	 * @return
	 * 
	 * Check if valid number
	 */
	public static boolean validateNumber(String number)
	{

		if (number.length() >20)
			return false;
		
		return number.matches("[0-9]+");

	}
}
