package org.ovirt.engine.ui.webadmin.section.main.view.tab.template;

import org.ovirt.engine.core.common.businessentities.VM;
import org.ovirt.engine.core.common.businessentities.VMStatus;
import org.ovirt.engine.core.common.businessentities.VmTemplate;
import org.ovirt.engine.ui.common.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.common.uicommon.model.SearchableDetailModelProvider;
import org.ovirt.engine.ui.common.widget.table.column.AbstractEnumColumn;
import org.ovirt.engine.ui.common.widget.table.column.AbstractTextColumn;
import org.ovirt.engine.ui.uicommonweb.models.templates.TemplateListModel;
import org.ovirt.engine.ui.uicommonweb.models.templates.TemplateVmListModel;
import org.ovirt.engine.ui.uicompat.external.StringUtils;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.gin.AssetProvider;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.template.SubTabTemplateVmPresenter;
import org.ovirt.engine.ui.webadmin.section.main.view.AbstractSubTabTableView;
import org.ovirt.engine.ui.webadmin.widget.table.column.AbstractUptimeColumn;
import org.ovirt.engine.ui.webadmin.widget.table.column.VmStatusColumn;
import org.ovirt.engine.ui.webadmin.widget.table.column.VmTypeColumn;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;

public class SubTabTemplateVmView extends AbstractSubTabTableView<VmTemplate, VM, TemplateListModel, TemplateVmListModel>
        implements SubTabTemplateVmPresenter.ViewDef {

    interface ViewIdHandler extends ElementIdHandler<SubTabTemplateVmView> {
        ViewIdHandler idHandler = GWT.create(ViewIdHandler.class);
    }

    private static final ApplicationConstants constants = AssetProvider.getConstants();

    @Inject
    public SubTabTemplateVmView(SearchableDetailModelProvider<VM, TemplateListModel, TemplateVmListModel> modelProvider) {
        super(modelProvider);
        initTable();
        initWidget(getTable());
    }

    @Override
    protected void generateIds() {
        ViewIdHandler.idHandler.generateAndSetIds(this);
    }

    void initTable() {
        getTable().enableColumnResizing();

        VmStatusColumn<VM> statusIconColumn = new VmStatusColumn<>();
        statusIconColumn.setContextMenuTitle(constants.statusIconVm());
        getTable().addColumn(statusIconColumn, constants.empty(), "30px"); //$NON-NLS-1$

        AbstractTextColumn<VM> nameColumn = new AbstractTextColumn<VM>() {
            @Override
            public String getValue(VM object) {
                return object.getName();
            }
        };
        getTable().addColumn(nameColumn, constants.nameVm(), "200px"); //$NON-NLS-1$

        VmTypeColumn typeColumn = new VmTypeColumn();
        typeColumn.setContextMenuTitle(constants.typeVm());
        getTable().addColumn(typeColumn, constants.empty(), "30px"); //$NON-NLS-1$

        AbstractTextColumn<VM> hostColumn = new AbstractTextColumn<VM>() {
            @Override
            public String getValue(VM object) {
                return object.getRunOnVdsName();
            }
        };
        getTable().addColumn(hostColumn, constants.hostVm(), "200px"); //$NON-NLS-1$

        AbstractTextColumn<VM> ipColumn = new AbstractTextColumn<VM>() {
            @Override
            public String getValue(VM object) {
                return object.getIp();
            }
        };
        getTable().addColumn(ipColumn, constants.ipVm(), "200px"); //$NON-NLS-1$

        AbstractTextColumn<VM> fqdnColumn = new AbstractTextColumn<VM>() {
            @Override
            public String getValue(VM object) {
                return object.getFqdn();
            }
        };
        getTable().addColumn(fqdnColumn, constants.fqdn(), "200px"); //$NON-NLS-1$

        AbstractTextColumn<VM> statusColumn = new AbstractEnumColumn<VM, VMStatus>() {
            @Override
            protected VMStatus getRawValue(VM object) {
                return object.getStatus();
            }
        };
        getTable().addColumn(statusColumn, constants.statusVm(), "200px"); //$NON-NLS-1$

        AbstractTextColumn<VM> uptimeColumn = new AbstractUptimeColumn<VM>() {
            @Override
            protected Double getRawValue(VM object) {
                return object.getRoundedElapsedTime();
            }
        };
        getTable().addColumn(uptimeColumn, constants.uptimeVm(), "200px"); //$NON-NLS-1$

        AbstractTextColumn<VM> consoleConnectedUserColumn = new AbstractTextColumn<VM>() {
            @Override
            public String getValue(VM vm) {
                return !StringUtils.isEmpty(vm.getClientIp())
                       ? vm.getConsoleCurentUserName()
                       : null;
            }
        };
        getTable().addColumn(consoleConnectedUserColumn, constants.consoleConnectedUserVm(), "200px"); //$NON-NLS-1$

        AbstractTextColumn<VM> loggedInUserColumn = new AbstractTextColumn<VM>() {
            @Override
            public String getValue(VM object) {
                return object.getGuestCurentUserName();
            }
        };
        getTable().addColumn(loggedInUserColumn, constants.loggedInUserVm(), "200px"); //$NON-NLS-1$
    }

}
