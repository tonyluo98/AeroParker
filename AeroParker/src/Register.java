
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param email Users email to store
	 * @return boolean true or false determines if it already exists in database
	 * 
	 *         Method checks if the user email exists in the database
	 */
	protected Boolean checkEmailExists(HttpServletRequest request, HttpServletResponse response, String email)
			throws ServletException, IOException {
		RegisterDao rDao = new RegisterDao();
		boolean result = rDao.checkEmailExists(email);
//		response.getWriter().print(result);
		return result;
	}

	/**
	 * @param customerId User id number
	 * @return boolean
	 * 
	 *         Method checks database if site has been registered under customer id
	 */
	protected Boolean checkIfSitesHaveBeenReg(HttpServletRequest request, HttpServletResponse response, int customerId)
			throws ServletException, IOException {
		RegisterDao rDao = new RegisterDao();
		boolean result = rDao.checkCustomerSiteRegister(customerId);
//		response.getWriter().print(result);
		return result;
	}

	/**
	 * @param customerId User id number
	 * @return list of sites
	 * 
	 *         Method checks all the sites that have been registered with the
	 *         customer id
	 */
	protected ArrayList<Integer> checkSiteId(HttpServletRequest request, HttpServletResponse response, int customerId)
			throws ServletException, IOException {
		ArrayList<Integer> siteIdList = new ArrayList<Integer>();
		RegisterDao rDao = new RegisterDao();
		siteIdList = rDao.selectRegisteredSitesId(customerId);
//		response.getWriter().print(result);
		return siteIdList;
	}

	/**
	 * @param email
	 * @return id
	 * 
	 *         Method returns customer id associated with email
	 */
	protected int getCustomerId(HttpServletRequest request, HttpServletResponse response, String email)
			throws ServletException, IOException {
		RegisterDao rDao = new RegisterDao();
		int id = rDao.selectCustomerId(email);
//		response.getWriter().print(result);
		return id;
	}

	/**
	 * @param site
	 * @return
	 * 
	 *         Method returns site id associated with name
	 */
	protected int getSiteId(HttpServletRequest request, HttpServletResponse response, String site)
			throws ServletException, IOException {
		RegisterDao rDao = new RegisterDao();
		int id = rDao.selectSiteId(site);
//		response.getWriter().print(result);
		return id;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

//		Validation checks on each property of customer
		boolean emailBool;
		boolean titleBool;
		boolean fnameBool;
		boolean lnameBool;
		boolean address1Bool;
		boolean address2Bool;
		boolean cityBool;
		boolean postcodeBool;
		boolean numberBool;
		boolean valid = true;
		boolean emailExists = false;
		boolean siteExists = false;
		String site = request.getParameter("site");
		String email = request.getParameter("email");
		email = email.toLowerCase();
		emailBool = Validate.validateEmail(email);
		if (emailBool == false) {
			response.getWriter().println("Invalid email");
			valid = false;
		}

		String title = request.getParameter("title");
		title = title.substring(0, 1).toUpperCase() + title.substring(1);
		titleBool = Validate.validateString(title, 5);
		if (titleBool == false) {
			response.getWriter().println("Invalid title");
			valid = false;
		}

		String fname = request.getParameter("fname");
		fnameBool = Validate.validateString(fname, 50);
		if (fnameBool == false) {
			response.getWriter().println("Invalid first name");
			valid = false;
		}

		String lname = request.getParameter("lname");
		lnameBool = Validate.validateString(lname, 50);
		if (lnameBool == false) {
			response.getWriter().println("Invalid last name");
			valid = false;
		}

		String address1 = request.getParameter("address1");
		address1Bool = Validate.validateString(address1, 255);
		if (address1Bool == false) {
			response.getWriter().println("Invalid address 1");
			valid = false;
		}

		String address2 = request.getParameter("address2");
		address2Bool = Validate.validateString(address2, 255);
		if (address2Bool == false) {
			response.getWriter().println("Invalid address 2");
			valid = false;
		}

		String city = request.getParameter("city");
		cityBool = Validate.validateCity(city);
		if (cityBool == false) {
			response.getWriter().println("Invalid city");
			valid = false;
		}

		String postcode = request.getParameter("postcode");
		postcode = postcode.toUpperCase();

		postcodeBool = Validate.validatePostcode(postcode);
		if (postcodeBool == false) {
			response.getWriter().println("Invalid postcode");
			valid = false;
		}

		String number = request.getParameter("number");
		numberBool = Validate.validateNumber(number);
		if (numberBool == false) {
			response.getWriter().println("Invalid telephone number");
			valid = false;
		}

		int siteId=0;
		RegisterDao rDao = null;
		String result;
		/**
		 * if the user inputs are valid then check to see if data can be added to
		 * database
		 * 
		 */
		rDao = new RegisterDao();
		if (valid) {
			// check if user email exists in the database
			// if it doesn't, customer is added to database and so is the corresponding site
			emailExists = checkEmailExists(request, response, email);
			if (!emailExists) {
				Customer customer = new Customer(email, title, fname, lname, address1, address2, city, postcode,
						number);
				result = rDao.insertCustomer(customer);
				System.out.println(result);

				int customerId = getCustomerId(request, response, email);
				siteId = getSiteId(request, response, site);

				CustomerSites customerSites = new CustomerSites(customerId, siteId);
				System.out.println("customer sites added");
				result = rDao.insertCustomerSite(customerSites);
				System.out.println(result);
				response.sendRedirect("Successful.jsp");
			} else {
				// if user email does exist, check to see if the site the user wants to register
				// is already registered
				// if not registered add to database
				boolean siteRegistered = false;
				int customerId = getCustomerId(request, response, email);
				boolean customerHasSitesReg = false;
				siteId = getSiteId(request, response, site);
				
				customerHasSitesReg = checkIfSitesHaveBeenReg(request, response, customerId);
				if (customerHasSitesReg) {
					ArrayList<Integer> siteIdList = checkSiteId(request, response, customerId);
					System.out.println("done");
					
					// compares the requested site to all existing registered sites under the
					// customerid
					for (int i = 0; i < siteIdList.size(); i++) {
						if (siteId == siteIdList.get(i)) {
							siteRegistered = true;
						}
					}
				}

				if (!siteRegistered) {
					CustomerSites customerSites = new CustomerSites(customerId, siteId);
					result = rDao.insertCustomerSite(customerSites);
					System.out.println(result);
					response.sendRedirect("Successful.jsp");
				} else {
					result = "Already registered";
					System.out.println(result);
					response.sendRedirect("AlreadyRegistered.jsp");
				}
			}

		}

	}

}
