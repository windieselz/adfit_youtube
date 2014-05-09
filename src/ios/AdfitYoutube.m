//
//  AdfitYoutube.m
//  AdfitYoutube
//
//  Created by Kittipong Kulapruk on 5/8/2557 BE.
//  Copyright (c) 2557 Kittipong Kulapruk. All rights reserved.
//

#import "AdfitYoutube.h"

@implementation AdfitYoutube

- (void) openYoutube:(CDVInvokedUrlCommand*)command{
	self.callbackId = command.callbackId;
    NSMutableDictionary* options = [command.arguments objectAtIndex:0];
    NSString * videoId = [options objectForKey:@"youtubeId"] ;
    adfit =  [[AdfitYoutubeViewController alloc] init];
    [adfit setYoutubeDelegate:self];
    [adfit setYoutubeId:videoId];
    //[self.viewController presentViewController:adfit animated:YES completion:nil];
    //[self presentModalViewController:adfit animated:YES];
    //[self.viewController presentModalViewController:adfit animated:YES];
    [self.viewController.view addSubview:adfit.view];
}

- (void) closeYoutubeWithFinish:(BOOL) finish{
	
	CDVPluginResult *commandResult;
	if(finish){
		commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"ended"];
	}else{
		commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"not ended"];
	}

	[self.commandDelegate sendPluginResult:commandResult callbackId:self.callbackId];
	[adfit.view removeFromSuperview];
    [adfit setYoutubeDelegate:nil];
	adfit = nil;

	/*
	[self.viewController dismissViewControllerAnimated:YES completion:^{
            
    }];
	*/

	//[self performSelectorOnMainThread:@selector(closeonMainThread) withObject:nil waitUntilDone:NO];
}

-(void) closeonMainThread{
	//[adfit dismissModalViewControllerAnimated:NO];
	[self.viewController dismissViewControllerAnimated:YES completion:nil];
}

@end
