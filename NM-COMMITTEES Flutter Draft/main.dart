import 'package:flutter/material.dart';
import 'package:anim/drawer.dart';
import 'package:anim/homes.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        body: Stack(
          children: [
            Drawer(),
            Homes(),
          ],
        ),
      ),
    );
  }
}
