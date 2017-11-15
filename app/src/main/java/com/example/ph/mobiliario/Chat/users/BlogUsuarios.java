package com.example.ph.mobiliario.Chat.users;

/**
 * Created by ph on 28/08/2017.
 */

public class BlogUsuarios {


        private String displayName;
        private String estatus;
        private String image;

        public BlogUsuarios()
        {

        }

        public BlogUsuarios(String displayName, String estatus, String image) {
            this.displayName = displayName;
            this.estatus = estatus;
            this.image = image;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }
