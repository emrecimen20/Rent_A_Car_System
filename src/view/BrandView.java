package view;

import business.BrandManager;
import core.Helper;
import entity.Brand;

import javax.swing.*;


public class BrandView extends Layout {
    private JPanel container;
    private JLabel lbl_brand;
    private JLabel lbl_brand_name;
    private JTextField field_brand_name;
    private JButton btn_brand_save;
    private Brand brand;
    private BrandManager brandManager;
    public BrandView(Brand brand) {
        this.brandManager=new BrandManager();
        this.brand=brand;
        this.add(container);
        this.guiInitilaze(300,200);

        if(brand !=null){
            this.field_brand_name.setText(brand.getName());
        }

        btn_brand_save.addActionListener(e -> {
            if(Helper.isFieldEmpty(this.field_brand_name)){
                Helper.showMssg("fill");
            }else{
                boolean result = true;
                if(this.brand== null){
                    Brand obj = new Brand(field_brand_name.getText());
                    result =this.brandManager.save(obj);
                }else{
                    this.brand.setName(field_brand_name.getText());
                    result = this.brandManager.update(this.brand);
                }
                if(result){
                    Helper.showMssg("done");
                    dispose();
                }else{
                    Helper.showMssg("error");
                }
            }
        });
    }
}
