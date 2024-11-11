import React from 'react'
import Quiz from '../screens/quiz'
import {Image, StyleSheet, Text, TouchableOpacity, View} from 'react-native'
import Title from '../components/title';

const Home = ({navigation}) => {
    return(
        <View style={styles.container}>
            <Title titleText='KWIZZZ'/>
            <View style={styles.bannerContainer}>
                <Image
                    source={{
                        uri: 'https://storyset.com/illustration/online-test/amico'
                    }}
                    style={styles.banner}
                    resizeMode="contain"
                /> 
            </View>
            <TouchableOpacity onPress={()=>navigation.navigate('Quiz')} style={styles.button}>
                <Text style={styles.buttontext}>Start</Text>
            </TouchableOpacity>
        </View>
    );
};

export default Home;

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
})

