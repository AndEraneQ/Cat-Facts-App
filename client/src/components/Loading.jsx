import React from 'react';
import styles from './Loading.module.css'; // Importuj plik CSS dla stylów ładowania

const Loading = () => {
  return (
    <div className={`${styles.load}`}>
      <div className={`${styles.loader}`}>G</div>
      <div className={`${styles.loader}`}>N</div>
      <div className={`${styles.loader}`}>I</div>
      <div className={`${styles.loader}`}>D</div>
      <div className={`${styles.loader}`}>A</div>
      <div className={`${styles.loader}`}>O</div>
      <div className={`${styles.loader}`}>L</div>
    </div>
  );
};

export default Loading;
