package biz.nirvani.storebiz.app.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.ExternalLink;

import biz.nirvani.storebiz.app.page.customer.registration.CustomerRegistrationPage;
import biz.nirvani.storebiz.app.page.manufacturer.listing.ManufacturerListingPage;
import biz.nirvani.storebiz.app.page.manufacturer.registration.ManufacturerRegistrationPage;
import biz.nirvani.storebiz.app.page.supplier.listing.SupplierListingPage;
import biz.nirvani.storebiz.app.page.supplier.registration.SupplierRegistrationPage;

import com.wicketinaction.HomePage;

public class ShopManagementHomePage extends AppBasePage {
	public ShopManagementHomePage(PageParameters parameters){
		super(parameters);
		String linkCustomerRegistrationPageUrl = (String)urlFor(CustomerRegistrationPage.class,parameters);
		add(new ExternalLink("linkCustomerRegistrationPage", linkCustomerRegistrationPageUrl));
		add(new ExternalLink("linkManufacturerRegistrationPage", (String) urlFor(ManufacturerRegistrationPage.class,parameters)));
		add(new ExternalLink("linkManufacturerListingPage",(String)urlFor(ManufacturerListingPage.class,parameters)));
		add(new ExternalLink("linkSupplierRegistrationPage", (String) urlFor(SupplierRegistrationPage.class,parameters)));
		add(new ExternalLink("linkSupplierListingPage",(String)urlFor(SupplierListingPage.class,parameters)));
		
		add(new ExternalLink("linkTestListEditor","#"));
		add(new ExternalLink("linkTestMultiFormPanel","#"));
		add(new ExternalLink("wicketInActionHomePage",(String)urlFor(HomePage.class,parameters)));
	}
}
