package biz.storebiz.app.page.supplier.registration;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import biz.storebiz.app.common.panel.address.editable.AddressEditablePanel;
import biz.storebiz.app.common.panel.address.editable.AddressEditablePanelModel;
import biz.storebiz.app.entity.view.AddressViewModel;
import biz.storebiz.app.entity.view.SupplierViewModel;
import biz.storebiz.app.page.SecurePage;
import biz.storebiz.app.page.supplier.detail.SupplierDetailPage;
import biz.storebiz.biz.common.constants.CAddressOwnerType;
import biz.storebiz.biz.common.constants.CAddressType;
import biz.storebiz.biz.common.constants.CEntityLifeStatus;
import biz.storebiz.biz.service.IBussinessService;
import biz.storebiz.biz.service.db.ISupplierDBService;
import biz.storebiz.biz.service.implementation.BusinessServiceImpl;
import biz.storebiz.utils.AppOpertionalUtility;

public class SupplierRegistrationPage extends SecurePage {

	private SupplierViewModel supplier;
	private AddressEditablePanelModel addressModel;

	private Form<?> supplierRegistrationForm;

	public SupplierRegistrationPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	public void renderPageBodyContent(PageParameters parameters) {
		// Set the default country as INDIA. As the application will only run
		// for India
		addressModel = new AddressEditablePanelModel();
		addressModel.setCountry("INDIA");
		addressModel.setCountryCode("IN");

		add(new FeedbackPanel("feedback"));

		supplierRegistrationForm = new Form<Void>("supplierRegistrationForm") {

			@Override
			public void onSubmit() {
				// Add the supplier to database and redirect to Supplier
				// detail page
				addressModel
						.setAddressOwnerType(CAddressOwnerType.ADDRESS_OWNER_TYPE_ORGANIZATION);
				addressModel
						.setAddressType(CAddressType.ADDRESS_TYPE_INDUSTRIAL);

				List<AddressViewModel> addrVMList = new ArrayList<AddressViewModel>();
				addrVMList.add(new AddressViewModel(addressModel));
				supplier.setPostalAddresses(addrVMList);
				// By default make the manufacturer status active.
				supplier.setStatus(CEntityLifeStatus.ACTIVE);
				supplier.setPrimaryAddressCountryCode(addrVMList.get(0)
						.getCountryCode());
				supplier.setPrimaryAddressCountry(addrVMList.get(0)
						.getCountry());
				supplier.setPrimaryAddressCountyCode(addrVMList.get(0)
						.getCountyCode());
				supplier.setPrimaryAddressCounty(addrVMList.get(0).getCounty());

				// Now add the Supplier in the DataBase
				ISupplierDBService suppDbsrv = BusinessServiceImpl
						.getInstance().getSupplierDBServiceInstance();
				supplier = suppDbsrv.put(supplier);

				// Direct the customer to Supplier detail page

				PageParameters pars = new PageParameters();
				pars.put(SupplierDetailPage.SUPPLIER_OBJECT, supplier);
				setResponsePage(new SupplierDetailPage(pars));
			}

		};
		add(supplierRegistrationForm);

		supplierRegistrationForm.add(new Label("supplierNameLabel", new Model(
				"Supplier Name")));

		TextField<String> supplierNameTxt = new TextField<String>(
				"supplierName",
				new PropertyModel(this, "supplier.supplierName"));
		supplierRegistrationForm.add(supplierNameTxt);
		AppOpertionalUtility.prepareFormComponents(supplierNameTxt, true,
				"Supplier's Name", 1, 150, true);

		AddressEditablePanel addressPanel = new AddressEditablePanel(
				"supplierAddress",
				new PropertyModel<AddressEditablePanelModel>(this,
						"addressModel"));
		supplierRegistrationForm.add(addressPanel);

	}

}
