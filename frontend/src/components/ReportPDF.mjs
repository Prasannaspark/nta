import React from 'react';
import { Document, Page, View, Text, StyleSheet } from '@react-pdf/renderer';
 
const styles = StyleSheet.create({
  page: {
    flexDirection: 'row',
    backgroundColor: '#ffffff',
  },
  section: {
    margin: 10,
    padding: 10,
    flexGrow: 1,
  },
  table: {
    display: 'table',
    width: 'auto',
    borderStyle: 'solid',
    borderWidth: 1,
    borderRightWidth: 0,
    borderBottomWidth: 0,
  },
  tableRow: {
    flexDirection: 'row',
  },
  tableColHeader: {
    borderStyle: 'solid',
    borderWidth: 1,
    borderLeftWidth: 0,
    borderTopWidth: 0,
    padding: 5,
    backgroundColor: '#f0f0f0',
  },
  tableCol: {
    borderStyle: 'solid',
    borderWidth: 1,
    borderLeftWidth: 0,
    borderTopWidth: 0,
    padding: 5,
  },
  tableCellHeader: {
    fontSize: 8,
    fontWeight: 'bold',
    wordWrap: 'break-word',
    textAlign: 'center',
  },
  tableCell: {
    fontSize: 8,
    wordWrap: 'break-word',
    textAlign: 'center',
  },
});
 
const ReportPDF = ({ data }) => {
  const keys = Object.keys(data[0]);
  const colWidth = `${100 / keys.length}%`;
 
  return (
    <Document>
      <Page size="A4" orientation="landscape" style={styles.page}>
        <View style={styles.section}>
          <View style={styles.table}>
            {/* Table Header */}
            <View style={styles.tableRow}>
              {keys.map((key, index) => (
                <View style={[styles.tableColHeader, { width: colWidth }]} key={index}>
                  <Text style={styles.tableCellHeader}>{key}</Text>
                </View>
              ))}
            </View>
 
            {/* Table Rows */}
            {data.map((item, rowIndex) => (
              <View style={styles.tableRow} key={rowIndex}>
                {keys.map((key, colIndex) => (
                  <View style={[styles.tableCol, { width: colWidth }]} key={colIndex}>
                    <Text style={styles.tableCell}>
                      {typeof item[key] === 'string' && item[key].length > 15
                        ? `${item[key].substring(0, 15)}...`
                        : item[key]}
                    </Text>
                  </View>
                ))}
              </View>
            ))}
          </View>
        </View>
      </Page>
    </Document>
  );
};
 
export default ReportPDF;