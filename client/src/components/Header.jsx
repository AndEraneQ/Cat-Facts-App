import React from "react";
import styles from './Header.module.css';

function Header(){
    return(
        <div className={styles.headerContainer}>
            <h1 className={styles.headerText}>Obsessed with Cats? Uncover Fascinating Facts Live!</h1>
        </div>
    );
}

export default Header;