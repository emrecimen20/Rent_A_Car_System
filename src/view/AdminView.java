package view;

import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Brand;
import entity.Car;
import entity.Model;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu_car;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scrl_model;
    private JTable tbl_model;
    private JLabel lbl_src_brand;
    private JComboBox cmb_model_brand;
    private JComboBox cmb_s_type;
    private JComboBox cmb_s_model_fuel;
    private JComboBox cmb_s_model_gear;
    private JButton btn_search_model;
    private JLabel lbl_src_type;
    private JLabel lbl_src_fuel;
    private JLabel lbl_src_gear;
    private JButton btn_clear_src;
    private JPanel pnl_car;
    private JTable table_car;
    private JPanel pnl_carr;
    private JScrollPane scr_car;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car =new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brand_menu;
    private JPopupMenu model_menu;
    private JPopupMenu car_menu;
    private Object[] col_model;
    private CarManager carManager;

    public AdminView(User user) {
        this.carManager=new CarManager();
        this.modelManager =new ModelManager();
        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitilaze(1000, 500);
        this.user = user;

        if (this.user == null) {
            dispose();
        }

        this.lbl_welcome.setText("Hoşgeldiniz " + this.user.getUsername());

        loadBrandTable();
        loadBrandComponent();


        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

        loadCarTable();
        loadCarComponent();

    }

    public void loadCarTable(){
        Object [] col_car ={"ID","Marka","Model","Plaka","Renk","KM","Yıl","Tip","Yakıt Türü","Vites"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length,this.carManager.findAll());
        createTable(this.tmdl_car,this.table_car,col_car,carList);
    }

    public void loadCarComponent(){
        this.car_menu =new JPopupMenu();
        tableRowSelect(this.table_car,car_menu);

        this.car_menu.add("Yeni").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        this.car_menu.add("Güncelle").addActionListener(e -> {
            int selectedModelId = this.getTableSelectedRow(table_car, 0);
            CarView carView = new CarView(this.carManager.getById(selectedModelId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        this.car_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedModelId = this.getTableSelectedRow(table_car, 0);
                if (this.carManager.delete(selectedModelId)) {
                    Helper.showMssg("done");
                    loadCarTable();
                } else {
                    Helper.showMssg("error");
                }
            }
        });
    }

    private void loadModelComponent() {
        this.model_menu =new JPopupMenu();
        tableRowSelect(this.tbl_model,this.model_menu);

        this.model_menu.add("Yeni").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });
        this.model_menu.add("Güncelle").addActionListener(e -> {
            int selectedModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectedModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });
        this.model_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedModelId = this.getTableSelectedRow(tbl_model, 0);
                if (this.modelManager.delete(selectedModelId)) {
                    Helper.showMssg("done");
                    loadModelTable(null);
                } else {
                    Helper.showMssg("error");
                }
            }
        });

        this.tbl_model.setComponentPopupMenu(model_menu);

        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_model_brand.getSelectedItem();
            int brandId =0;
            if(selectedBrand != null){
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    brandId,
                    (Model.Fuel)cmb_s_model_fuel.getSelectedItem(),
                    (Model.Gear)cmb_s_model_gear.getSelectedItem(),
                    (Model.Type)cmb_s_type.getSelectedItem()

            );
            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length,modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });

        this.btn_clear_src.addActionListener(e -> {
            this.cmb_s_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_model_brand.setSelectedItem(null);
            loadModelTable(null);
        });
    }

    public void loadModelTable(ArrayList<Object[]> modelList) {
       this.col_model = new Object[]{"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};
        if(modelList == null){
            modelList = this.modelManager.getForTable(this.col_model.length,this.modelManager.findAll());
        }
        createTable(this.tmdl_model,this.tbl_model,this.col_model,modelList);
    }

    public void loadModelFilter(){
        this.cmb_s_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_type.setSelectedItem(null);
        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);
        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();
    }
    public void loadModelFilterBrand(){
        this.cmb_model_brand.removeAllItems();
        for(Brand obj : brandManager.findAll()){
            this.cmb_model_brand.addItem(new ComboItem(obj.getId(),obj.getName()));
        }
        this.cmb_model_brand.setSelectedItem(null);
    }

    public void loadBrandTable() {
        Object[] col_brand = {"Marka ID", "Marka Adı"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.tmdl_brand.setColumnIdentifiers(col_brand);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
    }

    public void loadBrandComponent() {
        this.brand_menu = new JPopupMenu();
        tableRowSelect(this.tbl_brand, brand_menu);
        this.brand_menu.add("Yeni").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });
        });
        this.brand_menu.add("Güncelle").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });
        });
        this.brand_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
                if (this.brandManager.delete(selectBrandId)) {
                    Helper.showMssg("done");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                } else {
                    Helper.showMssg("error");
                }
            }
        });
    }
}