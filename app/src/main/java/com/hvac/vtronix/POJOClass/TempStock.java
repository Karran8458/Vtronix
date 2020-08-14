package com.hvac.vtronix.POJOClass;

import java.util.List;

public class TempStock {


    /**
     * name : Categories
     * image :
     * description : Quick Reference Tool For Installers and Service Contractors
     * url :
     * is_root : true
     * products : []
     * categories : [{"name":"Residential Thermostats","image":"residential_thermostats","description":"Digital Low Voltage Thermostats\n    Great For single stage GAS, OIL, Electric and HEAT PUMP systems \n    - Single stage HEAT, COOL, or HEAT/COOL\n    - 24 VAC power stealing\n    - R, W, Y, G, O, B terminals\n    - Large LCD  display with Permanent Backlight\n    - No batteries or Mercury\n    - Selectable cycles per hour\n    - Advanced P+I control\nProgrammable Model  \n    - Program 5-1-1/5-2 or 7 individual days in one model\n    - Upgrade without rewiring - common sub-base & plug","url":"","is_root":false,"products":[{"name":"TE86SB-501","image":"https://vtronix.com/40-home_default_2x/te86s-001.jpg","description":"TE86 Non Programmable, Vertical, Heat/Cool, Power Stealing - 24 VAC.","diagram_url":"https://vtronix.com/upload/diagrams/te86sb-501diagram.pdf","specs_url":"https://vtronix.com/upload/specs/TE86SB-501.pdf","url":""},{"name":"TE80SB-501","image":"https://vtronix.com/42-home_default_2x/te86s-001.jpg","description":"Programmable 7 day, Vertical, Heat/Cool, Power Stealing - 24 VAC.","diagram_url":"https://vtronix.com/upload/diagrams/te80sb-501diagram.pdf","specs_url":"https://vtronix.com/upload/specs/TE80SB-501.pdf","url":""}],"categories":[]}]
     */

    private String name;
    private String image;
    private String description;
    private String url;
    private boolean is_root;
    private List<?> products;
    private List<CategoriesBean> categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIs_root() {
        return is_root;
    }

    public void setIs_root(boolean is_root) {
        this.is_root = is_root;
    }

    public List<?> getProducts() {
        return products;
    }

    public void setProducts(List<?> products) {
        this.products = products;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class CategoriesBean {

        /**
         * name : Residential Thermostats
         * image : residential_thermostats
         * description : Digital Low Voltage Thermostats
         Great For single stage GAS, OIL, Electric and HEAT PUMP systems
         - Single stage HEAT, COOL, or HEAT/COOL
         - 24 VAC power stealing
         - R, W, Y, G, O, B terminals
         - Large LCD  display with Permanent Backlight
         - No batteries or Mercury
         - Selectable cycles per hour
         - Advanced P+I control
         Programmable Model
         - Program 5-1-1/5-2 or 7 individual days in one model
         - Upgrade without rewiring - common sub-base & plug
         * url :
         * is_root : false
         * products : [{"name":"TE86SB-501","image":"https://vtronix.com/40-home_default_2x/te86s-001.jpg","description":"TE86 Non Programmable, Vertical, Heat/Cool, Power Stealing - 24 VAC.","diagram_url":"https://vtronix.com/upload/diagrams/te86sb-501diagram.pdf","specs_url":"https://vtronix.com/upload/specs/TE86SB-501.pdf","url":""},{"name":"TE80SB-501","image":"https://vtronix.com/42-home_default_2x/te86s-001.jpg","description":"Programmable 7 day, Vertical, Heat/Cool, Power Stealing - 24 VAC.","diagram_url":"https://vtronix.com/upload/diagrams/te80sb-501diagram.pdf","specs_url":"https://vtronix.com/upload/specs/TE80SB-501.pdf","url":""}]
         * categories : []
         */

        private String name;
        private String image;
        private String description;
        private String url;
        private boolean is_root;
        private String product_count;
        private List<ProductsBean> products;
        private List<?> categories;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isIs_root() {
            return is_root;
        }

        public void setIs_root(boolean is_root) {
            this.is_root = is_root;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public List<?> getCategories() {
            return categories;
        }

        public void setCategories(List<?> categories) {
            this.categories = categories;
        }

        public String getProduct_count() {
            return product_count;
        }

        public void setProduct_count(String product_count) {
            this.product_count = product_count;
        }

        public static class ProductsBean {
            /**
             * name : TE86SB-501
             * image : https://vtronix.com/40-home_default_2x/te86s-001.jpg
             * description : TE86 Non Programmable, Vertical, Heat/Cool, Power Stealing - 24 VAC.
             * diagram_url : https://vtronix.com/upload/diagrams/te86sb-501diagram.pdf
             * specs_url : https://vtronix.com/upload/specs/TE86SB-501.pdf
             * url :
             */

            private String name;
            private String image;
            private String description;
            private String diagram_url;
            private String specs_url;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDiagram_url() {
                return diagram_url;
            }

            public void setDiagram_url(String diagram_url) {
                this.diagram_url = diagram_url;
            }

            public String getSpecs_url() {
                return specs_url;
            }

            public void setSpecs_url(String specs_url) {
                this.specs_url = specs_url;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
