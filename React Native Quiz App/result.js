import { TabRouter } from '@react-navigation/native';
import React from 'react'
import {Image, StyleSheet, Text, TouchableOpacity, View} from 'react-native'
import Title from '../components/title';

const Result = ({navigation, route}) => {
    const {score} = route.params
    const resultBanner=score>40?"https://storyset.com/illustration/winners/rafiki":"https://www.ravepubs.com/wp-content/uploads/2015/10/We-can-do-better.jpg"
    return(
    <View  style={styles.container}>
        <Title titleText='RESULTS'/>
        <Text style={styles.scoreValue}>{score}</Text>

        <View style={styles.bannerContainer}>
            <Image
                source={{uri: 'https://storyset.com/illustration/winners/rafiki'}}
                style={styles.banner}
                resizeMode="contain"
            />
        </View>
        <TouchableOpacity onPress={()=>navigation.navigate('Home')} style={styles.buttons}>
            <Text style={styles.buttontext}>Start again</Text>
        </TouchableOpacity>
    </View>
    );
};



export default Result;

const styles = StyleSheet.create({
    banner:{
        height:300,
        width:300,
    },
    bannerContainer:{
        justifyContent:'center',
        alignItems:'center',
        flex:1
    },
    container:{
        paddingTop:40,
        paddingHorizontal:20,
        height:'100%',
    },
    button:{
        width: '100%',
        backgroundColor:'#1A759F',
        padding:20,
        borderRadius:16,
        alignItems:'center',
        marginBottom:30,
    },
    buttontext:{
        fontSize:24,
        fontWeight:'600',
        color:'white',
    },
    scoreValue:{
        fontSize:24,
        fontWeight:'800',
        alignSelf:'center',
    },
})
