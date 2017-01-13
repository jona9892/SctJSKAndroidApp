package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation;

import com.example.jonathanspc.sctskapp.BE.BECategory;
import com.example.jonathanspc.sctskapp.BE.BEProduct;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGateway;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 30-12-2016.
 */
public class GatewayProduct implements IGateway<BEProduct> {

    private static GatewayProduct instance;
    private ArrayList<BEProduct> products;

    public static GatewayProduct getInstance(){
        if(instance == null)
            instance = new GatewayProduct();
        return instance;
    }

    private GatewayProduct(){
        products = new ArrayList<BEProduct>();
        BECategory category1 = new BECategory(1,"Brød");
        BECategory category2 = new BECategory(1,"Drikkevarer");
        products.add(new BEProduct(1,"Sandwhich", "En meget god sandwhich med salat, kylling og dressing", "http://thesandwichguide.com/wp-content/uploads/sandwich.jpg", 20, category1));
        products.add(new BEProduct(2,"Sandwhich2", "En meget god sandwhich med salat, kylling og dressing", "http://thesandwichguide.com/wp-content/uploads/sandwich.jpg", 20, category1));
        products.add(new BEProduct(3,"Sandwhich3", "En meget god sandwhich med salat, kylling og dressing", "http://thesandwichguide.com/wp-content/uploads/sandwich.jpg", 20, category1));
        products.add(new BEProduct(4,"Sandwhich4", "En meget god sandwhich med salat, kylling og dressing", "http://thesandwichguide.com/wp-content/uploads/sandwich.jpg", 20, category1));
        products.add(new BEProduct(5,"Sandwhich5", "En meget god sandwhich med salat, kylling og dressing", "http://thesandwichguide.com/wp-content/uploads/sandwich.jpg", 20, category1));
        products.add(new BEProduct(10,"pizza", "En meget god skinke pizza", "http://www.cicis.com/media/1138/pizza_trad_pepperoni.png", 20, category1));
        products.add(new BEProduct(11,"Pizza2", "En meget god pepperoni pizza", "http://www.cicis.com/media/1138/pizza_trad_pepperoni.png", 20, category1));
        products.add(new BEProduct(12,"Pizza3", "En meget god pølse pizza", "http://www.cicis.com/media/1138/pizza_trad_pepperoni.png", 20, category1));
        products.add(new BEProduct(6,"Mini Mælk", "En lille mini mælk til skolen", "https://skolemad.dk/lev61/ill/b107.jpg", 6, category2));
        products.add(new BEProduct(7,"Skummet Mælk", "En lille Skummet mælk til skolen", "https://www.arla.dk/globalassets/arla-dk/products/all-our-brands/arla/skolem_lk-skummetm_lk.png?preset=product-desktop", 6, category2));
        products.add(new BEProduct(8,"Let Mælk", "En lille Let mælk til skolen", "https://www.arla.dk/globalassets/arla-dk/products/all-our-brands/arla/skolem_lk-letm_lk.png?preset=product-desktop", 6, category2));
        products.add(new BEProduct(9,"Sød Mælk", "En lille Sød mælk til skolen", "https://www.arla.dk/globalassets/arla-dk/products/all-our-brands/arla/skolem_lk-s_dm_lk.png?preset=product-desktop", 6, category2));
        products.add(new BEProduct(13,"Mini Mælk1", "En lille mini mælk til skolen", "https://skolemad.dk/lev61/ill/b107.jpg", 6, category2));
        products.add(new BEProduct(14,"Skummet Mælk1", "En lille Skummet mælk til skolen", "https://www.arla.dk/globalassets/arla-dk/products/all-our-brands/arla/skolem_lk-skummetm_lk.png?preset=product-desktop", 6, category2));
        products.add(new BEProduct(15,"Let Mælk1", "En lille Let mælk til skolen", "https://www.arla.dk/globalassets/arla-dk/products/all-our-brands/arla/skolem_lk-letm_lk.png?preset=product-desktop", 6, category2));
        products.add(new BEProduct(16,"Sød Mælk1", "En lille Sød mælk til skolen", "https://www.arla.dk/globalassets/arla-dk/products/all-our-brands/arla/skolem_lk-s_dm_lk.png?preset=product-desktop", 6, category2));
    }
    @Override
    public ArrayList<BEProduct> getAll() {
        return products;
    }

    @Override
    public BEProduct getById(int id) {

        return products.get(id);
    }
}
