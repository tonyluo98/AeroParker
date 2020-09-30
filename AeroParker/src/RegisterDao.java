
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class RegisterDao {
	private String dburl = "jdbc:mysql://localhost:3306/AeroParker?serverTimezone=GMT";
	private String dbuname="root";
	private String dbpassword = "tony";
	private String dbdriver = "com.mysql.cj.jdbc.Driver";
	Connection con;
	/**
	 * starts connection between servlet and mysql database
	 */
	public RegisterDao() {
		loadDriver(dbdriver);
		con = getConnection();
	}

	public void loadDriver(String dbDriver)
	{
		try {
			Class.forName(dbdriver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() 
	{
		Connection con =null;
		try {
			con =DriverManager.getConnection(dburl, dbuname, dbpassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 * 
	 * Selects the ID of the user based on the email from database
	 */
	public int selectCustomerId(String email) {
		String result = "";
		int customerId =0;
		String sql = "SELECT `ID` FROM customers WHERE `E-Mail Address` = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				customerId = Integer.parseInt(resultSet.getString(1));
				System.out.print("selecting user id from email :"+customerId);
			}
			
		} catch (Exception e) {
			System.out.print(e);
		}
		return customerId;
		
	}

	/**
	 * 
	 * @param site
	 * @return
	 * 
	 * Selects the ID of the site based on the site name from database
	 */
	public int selectSiteId(String site) {
		String result = "";
		int siteId =0;
		String sql = "SELECT `ID` FROM sites WHERE `Name` = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, site);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				siteId = Integer.parseInt(resultSet.getString(1));
				System.out.print("siteid based on site name:" +siteId);
			}
			
		} catch (Exception e) {
			System.out.print(e);
		}
		return siteId;
		
	}

	/**
	 * 
	 * @param email
	 * @return
	 * 
	 * Checks if email is in database
	 */
	public boolean checkEmailExists(String email) {
		int result =0;
		boolean exists = false;
		String sql = "SELECT EXISTS(SELECT * FROM customers WHERE `E-Mail Address` = ?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				result = Integer.parseInt(resultSet.getString(1));
				System.out.println("Checking if email exists in database"  + result);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		if(result > 0) {
			exists = true;
		}
		return exists;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * Check if the customer has any registered sites
	 */
	public boolean checkCustomerSiteRegister(int customerId) {
		String result = "";

		String sql =  "SELECT EXISTS(SELECT * FROM `customer sites` WHERE `Customer_id` = ?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, customerId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				result = resultSet.getString(1);
			}
			
			
		} catch (Exception e) {
			System.out.print(e);
		}
		
		boolean exists = false;
		int total = Integer.parseInt(result);
		if (total > 0) {
			exists= true;
		}
		System.out.print("Checking if there are any associated sites" + exists);		
		return exists;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * compiles list of sites registered under customer id
	 */
	public ArrayList<Integer> selectRegisteredSitesId(int customerId) {
		String result = "";
		ArrayList<Integer> siteIdList= new ArrayList<Integer>();
		String sql = "SELECT `Site_id` FROM `customer sites` WHERE `Customer_id` = ?";
		int sitesId=0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, customerId);
			ResultSet resultSet = ps.executeQuery();

//			int i =1;
//			while (resultSet.next()) {
//				sitesId= Integer.parseInt(resultSet.getString(i));
//				siteIdList.add(sitesId);
//				System.out.print("List of sites under customer:"+sitesId);
//				i ++;
//			}
			
			ResultSetMetaData metadata = resultSet.getMetaData();
			int numberOfColumns = metadata.getColumnCount();
			while (resultSet.next()) {
				int i = 1;
			    while(i <= numberOfColumns) {
			    	siteIdList.add(Integer.parseInt(resultSet.getString(i++)));
			    }
				System.out.print("List of sites under customer:"+sitesId);
			}
			
		} catch (Exception e) {
			System.out.print(e);
		}
		return siteIdList;
		
	}

	/**
	 * @param customer
	 * @return
	 * 
	 * Adds customer to database
	 */
	public String insertCustomer(Customer customer) 

	{

		String sql="insert into customers(`Registered`, `E-Mail Address`, `Title`,`First Name`, `Last Name`, `Address Line 1`, `Address Line 2`,`City`,`Postcode`,`Telephone Number`) values(?,?,?,?,?,?,?,?,?,?) ";

		String result ="data entered successfully";

	    String email = customer.getEmail();
	    String title = customer.getTitle();
	    String fname = customer.getFname();
	    String lname = customer.getLname();
	    String address1 = customer.getAddress1();
	    String address2 = customer.getAddress2();
	    String city = customer.getCity();
	    String postcode = customer.getPostcode();
	    String number = customer.getNumber();
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setTimestamp(1,timestamp);
			ps.setString(2, email);
			ps.setString(3, title);
			ps.setString(4, fname);
			ps.setString(5, lname);
			ps.setString(6, address1);
			ps.setString(7, address2);
			ps.setString(8, city);
			ps.setString(9, postcode);
			ps.setString(10, number);
			int i =ps.executeUpdate();
			System.out.print("customer added");
		} catch (Exception e) {
			System.out.print(e);
			result = "Error data fail";
		}
		return result;
	}
	
	/**
	 * 
	 * @param customerSites
	 * @return
	 * 
	 * Adds site to database under customer
	 */
	public String insertCustomerSite(CustomerSites customerSites)
	{
	
	String sql="insert into `customer sites`(`Customer_id`, `Site_id`) values(?,?) ";
	String result ="data entered successfully";

    int customerId = customerSites.getCustomerId();
    int siteId= customerSites.getSiteId();

    System.out.println("added customer site with id : "+customerId + " and site:"+siteId);
	try {
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1,customerId);
		ps.setInt(2, siteId);

		int i =ps.executeUpdate();
	} catch (Exception e) {
		System.out.print(e);
		result = "Error data fail";
	}
	return result;
	}
	
}
